import React, { useState, useEffect } from 'react'
import { useNavigate } from "react-router-dom";

export default function Profile() {

    const navigate = useNavigate();
    const userId = window.sessionStorage.getItem("userId");
    const backgroundStyle = {
        background: "eee;"
    };
    const width = {
        width: "150px;"
    }

    
    const [username, setUsername] = useState("");
    const [genres, setGenres] = useState([]);
    const [favoriteBooks, setFavoriteBooks] = useState([]);
    const [aboutMe, setAboutMe] = useState('');
    const [contactInfo, setContactInfo] = useState('');
    const [location, setLocation] = useState('');
    const [bookList, setBookList] = useState([]);
    const [profilePictureFileName, setProfilePictureFileName] = useState('');
    const [profilePicturePath, setProfilePicturePath] = useState('http://localhost:8080/images/');

    function deleteUserCopy(userCopyId) {
        fetch("http://localhost:8080/deleteUserCopy"+userCopyId, {
            method: "DELETE",
            headers: {
                "content-type": "text/plain"
            },
        })
        //If I use useEffect correctly, I feel like this shouldn't be necessary
        //Need to get this to update automatically without forced refresh along with favorite genre and book in EditProfile
        //Also does it make more sense to move this to EditProfile?
        //I could also add a 'add book' button to editProfile
        return navigate(0);
    }


    useEffect(() => {

        const fetchProfilePicture = async () => {
            await fetch("http://localhost:8080/getProfilePictureFileName/"+userId, {
            method: "GET",
            headers: {
                "content-type": "text/plain"
            },
            })
            .then((response) => response.text())
            .then((data) =>  {
            setProfilePictureFileName(data);
            setProfilePicturePath(profilePicturePath + profilePictureFileName)
            })
            .catch((error) => error);
        };

        const fetchUsername = async () => {
            await fetch("http://localhost:8080/getUsername/"+userId, {
            method: "GET",
            headers: {
                "content-type": "text/plain"
            },
            })
            .then((response) => response.text())
            .then((data) => {
                setUsername(data);
                window.sessionStorage.setItem("username",data)
            })
            .catch((error) => error);
        };

        const fetchGenres = async () => {
            await fetch("http://localhost:8080/getGenres/"+userId, {
            method: "GET",
            headers: {
                "content-type": "application/json"
            },
            })
            .then((response) => response.json())
            .then((data) => {
                    setGenres(data);
            })
            .catch((error) => error);
        };

        const fetchFavoriteBooks = async () => {
            await fetch("http://localhost:8080/getFavoriteBooks/"+userId, {
            method: "GET",
            headers: {
                "content-type": "application/json"
            },
            })
            .then((response) => response.json())
            .then((data) => {
                    setFavoriteBooks(data);
            })
            .catch((error) => error);
        };

        const fetchAboutMe = async () => {
            await fetch("http://localhost:8080/getAboutMe/"+userId, {
            method: "GET",
            headers: {
                "content-type": "text/plain"
            },
            })
            .then((response) => response.text())
            .then((data) => {
                setAboutMe(data);
            })
            .catch((error) => error);
        };

        const fetchLocation = async () => {
            await fetch("http://localhost:8080/getLocation/"+userId, {
            method: "GET",
            headers: {
                "content-type": "text/plain"
            },
            })
            .then((response) => response.text())
            .then((data) => {
                setLocation(data);
            })
            .catch((error) => error);
        };

        const fetchContactInfo = async () => {
            await fetch("http://localhost:8080/getContactInfo/"+userId, {
            method: "GET",
            headers: {
                "content-type": "text/plain"
            },
            })
            .then((response) => response.text())
            .then((data) => {
                setContactInfo(data);
            })
            .catch((error) => error);
        };

        const fetchBookList = async () => {
            await fetch("http://localhost:8080/getUserBookList/"+userId, {
            method: "GET",
            headers: {
                "content-type": "application/json"
            },
            })
            .then((response) => response.json())
            .then((data) => {
                setBookList(data);
            })
            .catch((error) => error);
        };

        
        fetchContactInfo();
        fetchLocation();
        fetchAboutMe();
        fetchFavoriteBooks();
        fetchUsername();
        fetchGenres();
        fetchBookList();
        fetchProfilePicture();
    },[userId,profilePictureFileName]);

    window.sessionStorage.setItem("username", username);

    return (
    <body class="text-center bg">
        <section style={backgroundStyle}>
            <div id="white-bg" class="container p-5 d-flex h-100 mx-auto flex-column align-items-center">
                <div class="row p-5">
                    <div class="col-lg-4">
                        <div class="card mb-4">
                            <div class="card-body text-center">
                                <img src={profilePicturePath !== 'http://localhost:8080/images/' ? profilePicturePath : "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png"} alt="Profile Picture"
                                class="rounded-circle img-fluid" style={width}></img>
                                <h5 class="my-3">{username}</h5>
                                <p class="mb-0">About me:</p><br />
                                <p class="text-muted mb-1">{aboutMe}</p><br />
                                <div class="d-flex justify-content-center mb-2">
                                    <a href="/EditProfile" class="btn btn-primary">Edit Profile</a>
                                </div>
                            </div>
                        </div>
                    </div>   
                    <div class="col-lg-8">
                        <div class="card mb-4">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">Username</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">{username}</p>
                                    </div>
                                </div>
                                <hr />
                                <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">Contact me</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">{contactInfo}</p>
                                    </div>
                                </div>
                                <hr />
                                <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">Location</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">{location}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    
                
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <div class="card mb-4 mb-md-0">
                            <div class="card-body">
                                <p class="mb-4"><span class="text-secondary font-italic me-1">Favorite</span> Books
                                </p>
                                <div class="row">
                                    <ul class="list-group mb-1"> 
                                    {
                                        favoriteBooks.map(
                                            book =>
                                            <li class="list-group-item">{book.bookName}</li>
                                        )
                                    }
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card mb-4 mb-md-0">
                            <div class="card-body">
                                <p class="mb-4"><span class="text-secondary font-italic me-1">Favorite</span> Genres
                                </p>
                                <div class="row">
                                    <ul class="list-group mb-1"> 
                                    {
                                        genres.map(
                                            genre =>
                                            <li class="list-group-item">{genre.genreName}</li>
                                        )
                                    }
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div>
                        <div class="card mb-4 mb-md-0">
                            <div class="card-body">
                                <p class="mb-4"><span class="text-secondary font-italic me-1">My</span> Books
                                </p>
                                <div class="mt-5 mx-auto">
                                    {
                                        bookList.map(
                                            userCopy =>
                                            <div style={{justifyContent: "left", display: "flex", flexDirection: "row", margin:"1em"}}>
                                                <img style={{width:100 ,height: 150, marginRight: "2em"}} src={userCopy.book.coverUrl ? userCopy.book.coverUrl : "https://upload.wikimedia.org/wikipedia/commons/6/65/No-Image-Placeholder.svg"} alt={userCopy.book.title}></img>
                                                <span class="m-5">{userCopy.book.title}</span><div class="m-5"><button class="btn btn-secondary" onClick={() => deleteUserCopy(userCopy.id)}>X</button></div>
                                            </div>
                                        )
                                    }
                                    <a class="btn btn-secondary" href="/addBook">Add a Book</a>    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
    </section>
    </body>

                        
        );
    }