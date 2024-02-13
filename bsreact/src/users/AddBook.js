import React, { useState, useEffect } from 'react';

export default function AddBook() {

const userId = window.sessionStorage.getItem("userId");
const [books, setBooks] = useState([]);
//Do I need to make this a book object?
//Or would I select one book object from array of books?
const [book, setBook] = useState('');
const [searchTerm, setSearchTerm] = useState('');
const key = window.sessionStorage.getItem("key");

const handleInputChangeSearch = (e) => {
    setSearchTerm(e.target.value);
    //make GB api call here
}
function handleSubmitSearch() {
    fetch("https://www.googleapis.com/books/v1/volumes?q="+{searchTerm}+"&key="+{key}, {
        method: "GET",
        headers: {
            "content-type": "application/json",
        },
        })
        .then((response) => response.json())
        .then((data) => {
            setBooks(data);
        })
        //is .catch((error) => error); needed here? what does it do? Reserach this
        console.log(key);
        console.log(searchTerm);
        console.log(books);
};

// useEffect(() => {

//     //if handleInputChangeSearch function makes GB api call is this necessary??
//     const handleSubmitSearch = async () => {
//         await fetch("https://www.googleapis.com/books/v1/volumes?q="+{searchTerm}+"&key="+{key}, {
//             method: "GET",
//             headers: {
//                 "content-type": "application/json"
//             },
//             })
//             .then((response) => response.json())
//             .then((data) => {
//                 setBooks(data);
//             })
//             //is .catch((error) => error); needed here? what does it do? Reserach this
//             console.log(books);
//     };

//     const addBook = async () => {
//         //api call for onChange input
//     };

//     handleSubmitSearch();
//     addBook();

//     },[]);

    return(
        <body>
            <form method="Get" onSubmit={handleSubmitSearch}>
                <input type="text" name="searchTerm" value={searchTerm} placeholder='Add a book' onChange={handleInputChangeSearch}/>
                <input type="submit" value="Search"/>
            </form>
            <p>
                {books}
            </p>
        </body>
    )


}