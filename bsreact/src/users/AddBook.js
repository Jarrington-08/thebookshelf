import React, { useState, useEffect } from 'react';

export default function AddBook() {

const userId = window.sessionStorage.getItem("userId");
const [books, setBooks] = useState([]);
//Do I need to make this a book object?
//Or would I select one book object from array of books?
const [book, setBook] = useState('');
const [searchTerm, setSearchTerm] = useState('');
// const key = window.sessionStorage.getItem("key");
const key = "AIzaSyD9ff8jAsbKpTfVfIAwdfBInX5AlgYMsWo";

const handleInputChangeSearch = (e) => {
    setSearchTerm(e.target.value);
    //make GB api call here
}
function handleSubmitSearch(event) {
            searchTerm.toString();
            event.preventDefault();
            fetch("https://content-books.googleapis.com/books/v1/volumes?q="+searchTerm+"&key="+key+"&maxResults=6", {
            "headers": {
        },
        "body": null,
        "method": "GET"
        })
        .then((response) => response.json())
        .then((data) => {
            setBooks(data.items);
        })
        //is .catch((error) => error); needed here? what does it do? Reserach this
        console.log(key);
        console.log(searchTerm);
        // console.log(books.items[0].volumeInfo.title);
};


    return(
        <body class="text-center bg">
            <div id="white-bg" class="container d-flex h-100 p-5 mx-auto flex-column align-items-top">
                <h2 style={{margin: "3rem"}}>Add a book to your personal collection:</h2>
                <div class="row d-flex align-items-center">
                        <form method="Get" onSubmit={handleSubmitSearch}>
                            <input type="text" name="searchTerm" value={searchTerm} placeholder='Search by title or author' class="form-control mb-2 w-25 mx-auto" onChange={handleInputChangeSearch}/>
                            <input type="submit" class="btn btn-secondary mt-2" value="Search"/>
                        </form>
                </div>
                <div class="m-5 p-5">
                    {books ? books.map(
                        book =>
                        <div style={{justifyContent: "left", display: "flex", flexDirection: "row"}}>
                            <p key={book.id}><img style={{width:100 ,height: 150}} src={book.volumeInfo.imageLinks ? book.volumeInfo.imageLinks.smallThumbnail : "https://upload.wikimedia.org/wikipedia/commons/6/65/No-Image-Placeholder.svg"} alt={book.volumeInfo.title}></img> <a class="link-secondary" href={book.volumeInfo.infoLink} target="_blank" rel="noreferrer noopener">{book.volumeInfo.title}</a> by {book.volumeInfo.authors} {book.volumeInfo.publishedDate ? book.volumeInfo.publishedDate.slice(0,4) : ""} 
                            </p>
                        </div>
                            ) : '"'+searchTerm+'"'+" did not return any results."
                    }
                </div>
            </div>
        </body>
    )


}