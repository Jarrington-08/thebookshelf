import React, { useState } from 'react'

export default function UpdateProfilePicture() {

    const userId = window.sessionStorage.getItem("userId");
    const [pictureFile, setPictureFile] = useState();  
    const [picturePreview, setPicturePreview] = useState();
    const [isUpdateProfilePicture, setIsUpdateProfilePicture] = useState(false);

    function handleChangePicFile(event) {
        setPictureFile(event.target.files[0]);
        setPicturePreview(URL.createObjectURL(event.target.files[0]));
      }

    const handleSubmitPictureFile = (e) => {
        //this may not be necessary
        e.preventDefault();
        var data = new FormData();
        data.append('file', pictureFile)
        fetch("http://localhost:8080/updateProfilePicture"+userId, {
            method: "POST",
            headers: {
                "content-type": "multipart/form-data",
            },
            body: data
        })
        setIsUpdateProfilePicture(false);
    }

    return (
        <form class="form-control" onSubmit={handleSubmitPictureFile}>
            <input type="file" id="picFile" name="filename" class="btn btn-secondary mb-2" onChange={handleChangePicFile}></input>
            <button type="submit" class="btn btn-secondary">Submit</button>
            <div>
                <p>New Picture Preview:</p>
                <img src={picturePreview} class="rounded-circle img-fluid" alt=""/>
            </div>
        </form>
    )
}