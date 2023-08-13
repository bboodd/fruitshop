package com.shop.fruitshop.admin;

import com.shop.fruitshop.domain.Admin;
import com.shop.fruitshop.domain.Product;
import com.shop.fruitshop.domain.ProductImage;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final FireBaseService fireBaseService;

    @GetMapping("{pathName}")
    public String path(@PathVariable String pathName,
                       @SessionAttribute(name = "loginAdmin", required = false) Admin loginAdmin,
                       Model model) {
        if (loginAdmin == null) {
            return "adminIndex";
        }

        model.addAttribute("admin", loginAdmin);
        return "admin/"+pathName;
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    void noFavicon() {
    }

    @GetMapping("product")
    public String product(Model model){
        model.addAttribute("count", adminService.countStatusAll());
        model.addAttribute("list", adminService.selectProductAll());

        return "admin/product";
    }

    @ResponseBody
    @PostMapping("/product")
    public HashMap<String, Object> product(@RequestBody HashMap<String, Object> param){

        List<HashMap<String, Object>> data = adminService.selectProductList(param);
        int count = adminService.countProducts(param);

        HashMap<String,Object> data_count = new HashMap<>();

        data_count.put("data",data);
        data_count.put("count",count);

        return data_count;
    }

    @PostMapping("/login")
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

    @PostMapping("/addProduct")
    @ResponseBody
    public int addProduct(@Valid Product form,
                             @RequestParam("file") List<MultipartFile> file,
                             BindingResult bindingResult,
                             Model model) throws IOException{
        if(bindingResult.hasErrors()){
            return 1;
        }

        String firebaseContent = null;
        List<ProductImage> images = new ArrayList<>();

        for(MultipartFile multipartFile : file){
            String url = fireBaseService.uploadFiles(multipartFile, "images", multipartFile.getOriginalFilename());
            firebaseContent = form.getContent().replaceAll("<img[^>]*src=[\"']([^\"^']*)[\"'][^>]*>", "<img src=\"" + url + "\" />");

            ProductImage image = ProductImage.builder()
                    .filePath("images")
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

    @RequestMapping("/productStopAndDelete")
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

    @RequestMapping("/productNameCheck")
    @ResponseBody
    public int productNameCheck(@RequestBody HashMap<String, String> param){

        return adminService.productNameCheck(param);
    }

}
