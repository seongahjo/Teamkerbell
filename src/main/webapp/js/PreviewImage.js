/**
 * Created by hootting on 2016. 2. 7..
 */

function previewImage(targetObj,preimage){

    var preview = document.getElementById('preimage'); //div id

    var files = targetObj.files;
    for(var i = 0; i< files.length; i++)
    {
        var file= files[i];
        var imageType=/image.*/;//spread if image
        if(!file.type.match(imageType))
            continue;

        var prevImg = document.getElementById("prev_"+preimage); //delete if preview image is existed
        if(prevImg){
            preview.removeChild(prevImg);
        }
        //Chrome, impossible to insert image to div , so make child Element
        var img=document.createElement("img");
        img.id="prev_"+preimage;
        img.classList.add("obj");
        img.file=file;
        img.style.width='100px'; //fix the width of idv
        img.style.height='100px';

        preview.appendChild(img);

        if(window.FileReader){
            var reader =new FileReader();
            reader.onloadend =(function(aImg){
                return function(e){
                    aImg.src=e.target.result;
                };
            })(img);
            reader.readAsDataURL(file);
        }
    }

}