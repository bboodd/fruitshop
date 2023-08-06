tinymce.init({
    selector : '#content',
    plugins : 'image',
    // menubar : false,
    width : 900,
    height : 500,
    toolbar : 'link image',
    images_reuse_filename: true,
    automatic_uploads: true,
    images_upload_handler: imageUploadHandler
});

function imageUploadHandler(blobInfo, success, failure){

    const formData = new FormData();
    formData.append('file', blobInfo.blob(), blobInfo.filename());
    console.log(formData.get('file'));

    axios({
        method: 'post',
        url: '/admin/imageUploadHandler',
        data: formData
    }).then(res => {
        const url = res.data
        success(url);
    }).catch(err => {
        console.log("업로드 에러 : ", err);
    });
};