package com.shop.fruitshop.admin;

import com.github.pagehelper.PageInfo;
import com.shop.fruitshop.domain.Admin;
import com.shop.fruitshop.domain.Product;
import com.shop.fruitshop.domain.ProductImage;
import com.shop.fruitshop.domain.User;
import com.shop.fruitshop.firebase.FireBaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final FireBaseService fireBaseService;
    private final DecimalFormat formatter = new DecimalFormat("###,###");

    @GetMapping("admin/{pathName}")
    public String path(@PathVariable String pathName,
                       @SessionAttribute(name = "loginAdmin", required = false) Admin loginAdmin,
                       Model model) {
        if (loginAdmin == null) {
            return "adminIndex";
        }

        model.addAttribute("admin", loginAdmin);
        return "admin/"+pathName;
    }

    @GetMapping("admin/favicon.ico")
    @ResponseBody
    void noFavicon() {
    }

    @GetMapping("admin/product")
    public String product(@RequestParam(required = false, defaultValue = "1") int pageNum,
                          @RequestParam(required = false, defaultValue = "10") int pageSize,
                          Model model){
        model.addAttribute("count", adminService.countStatusAll());
        model.addAttribute("list", adminService.selectProductAllWithPaging(pageNum, pageSize));

        return "admin/product";
    }

    @ResponseBody
    @PostMapping("/admin//product")
    public HashMap<String, Object> product(@RequestBody HashMap<String, Object> param){

        PageInfo<HashMap<String, Object>> data = adminService.selectProductListWithPaging(param);
        int count = adminService.countProducts(param);

        HashMap<String,Object> data_count = new HashMap<>();

        data_count.put("data",data);
        data_count.put("count",count);

        return data_count;
    }

    @PostMapping("/admin//login")
    public String login(Admin admin, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()){
            return "adminIndex";
        }

        HttpSession session = request.getSession();

        Admin loginAdmin = adminService.findById(admin.getId());

        if (loginAdmin != null) {
            String rawPw = admin.getPassword();
            String encodePw = loginAdmin.getPassword();

            if (rawPw.equals(encodePw)) {   //비밀번호 일치 여부 판단
                session.setAttribute("loginAdmin", loginAdmin);
            }
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin//addProduct")
    @ResponseBody
    public int addProduct(@Valid Product form,
                             @RequestParam("file") List<MultipartFile> file,
                             @RequestParam("mainImage") MultipartFile mainImage,
                             BindingResult bindingResult) throws IOException{
        if(bindingResult.hasErrors()){
            return 1;
        }

        String firebaseContent = null;
        List<ProductImage> images = new ArrayList<>();

        String mainUrl = fireBaseService.uploadFiles(mainImage, "mainImages", mainImage.getOriginalFilename());

        ProductImage main = ProductImage.builder()
                .filePath("mainImages")
                .fileName(mainImage.getOriginalFilename())
                .url(mainUrl)
                .build();

        images.add(main);

        for(MultipartFile multipartFile : file){
            String url = fireBaseService.uploadFiles(multipartFile, "contentImages", multipartFile.getOriginalFilename());
            firebaseContent = form.getContent().replaceAll("<img[^>]*src=[\"']([^\"^']*)[\"'][^>]*>", "<img src=\"" + url + "\" />");

            ProductImage image = ProductImage.builder()
                    .filePath("contentImages")
                    .fileName(multipartFile.getOriginalFilename())
                    .url(url)
                    .build();
            images.add(image);
        }
        form.setContent(firebaseContent);
        form.setImages(images);
        adminService.addProductAndImage(form);

        return 0;
    }

    //상품수정
    @GetMapping("admin/product/{id}/edit")
    public String editProduct(@PathVariable Long id, Model model) {

        Product product = adminService.findProductById(id);
        ProductImage mainImage = adminService.findMainImageById(id);

        ProductForm form = ProductForm.builder()
                .id(product.getId())
                .categoryId(product.getCategoryId())
                .price(product.getPrice())
                .discountRate(product.getDiscountRate())
                .stockQuantity(product.getStockQuantity())
                .name(product.getName())
                .content(product.getContent())
                .url(mainImage.getUrl())
                .build();

        model.addAttribute("form", form);
        return "admin/editProduct";
    }

    @PostMapping("/admin//editProduct")
    @ResponseBody
    public int editProduct(@Valid Product form,
                           @RequestParam("id") Long id,
                           @RequestParam(value = "file", required = false) List<MultipartFile> file,
                           @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
                           BindingResult bindingResult) throws IOException{

        if(bindingResult.hasErrors()){
            return 1;
        }

        String firebaseContent = null;
        List<ProductImage> images = new ArrayList<>();

        //메인 이미지 교체
        if(mainImage == null || mainImage.isEmpty()){
            System.out.println("mainImage is empty");
        } else {
            ProductImage beforeMain = adminService.findMainImageById(id);

            String mainUrl = fireBaseService.uploadFiles(mainImage, "mainImages", mainImage.getOriginalFilename());

            ProductImage afterMain = ProductImage.builder()
                    .id(beforeMain.getId())
                    .fileName(mainImage.getOriginalFilename())
                    .url(mainUrl)
                    .build();

            adminService.editProductImage(afterMain);
        }

        //내용 이미지 추가
        if(file == null || file.isEmpty()){
            System.out.println("file is empty");
        } else {
            for(MultipartFile multipartFile : file){
                String url = fireBaseService.uploadFiles(multipartFile, "contentImages", multipartFile.getOriginalFilename());
                firebaseContent = form.getContent().replaceAll("<img[^>]*src=[\"']([^\"^']*)[\"'][^>]*>", "<img src=\"" + url + "\" />");

                ProductImage image = ProductImage.builder()
                        .productId(id)
                        .filePath("contentImages")
                        .fileName(multipartFile.getOriginalFilename())
                        .url(url)
                        .build();
                images.add(image);
            }
            form.setContent(firebaseContent);
            form.setImages(images);

            adminService.editProductAndAddImage(form);

            return 0;
        }

        adminService.editProduct(form);

        //내용 이미지 삭제 -> 파이어베이스도 삭제해야하나?? 상품처럼 삭제상태? 그냥삭제?
//        String content = form.getContent();

        return 0;
    }

    @RequestMapping("/admin//productStopAndDelete")
    @ResponseBody
    public int productStopAndDelete(@RequestBody HashMap<String, Object> param){

        if(param.get("selectedStopId")!=null) {

            return adminService.saleStopOne(param);
        }

        if(param.get("selectedStopIds")!=null) {

            return adminService.saleStopMany(param);
        }

        if(param.get("selectedDeleteIds")!=null) {

            return adminService.productDeleteMany(param);
        }

        return 0;
    }

    @RequestMapping("/admin//productNameCheck")
    @ResponseBody
    public int productNameCheck(@RequestBody HashMap<String, String> param){

        return adminService.productNameCheck(param);
    }

}
