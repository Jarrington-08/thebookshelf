import React, { useState, useEffect } from 'react';

export default function AddBook() {

const userId = window.sessionStorage.getItem("userId");
const [books, setBooks] = useState([]);
const [currentPage, setCurrentPage] = useState(1);
const [recordsPerPage] = useState(5);
const indexOfLastRecord = currentPage * recordsPerPage;
const indexOfFirstRecord = indexOfLastRecord - recordsPerPage;
const currentRecords = books ? books.slice(indexOfFirstRecord, indexOfLastRecord) : null;
const [isDataRetrieved, setIsDataRetrieved] = useState(false);
// const [isNoResults, setIsNoResults] = useState(false);
//Do I need to make this a book object?
//Or would I select one book object from array of books?
const [book, setBook] = useState('');
const [searchTerm, setSearchTerm] = useState('');
const [searchError, setSearchError] = useState('');
const [noResults, setNoResults] = useState('');

// const key = window.sessionStorage.getItem("key");
const key = "AIzaSyD9ff8jAsbKpTfVfIAwdfBInX5AlgYMsWo";

const handleInputChangeSearch = (e) => {
    setSearchTerm(e.target.value);
    //make GB api call here
}
function handleSubmitSearch(event) {
            if (searchTerm === "") {
                setSearchError("Please enter search terms");
                event.preventDefault();
            }
            searchTerm.toString();
            event.preventDefault();
            fetch("https://content-books.googleapis.com/books/v1/volumes?q="+searchTerm+"&key="+key+"&maxResults=20", {
            "headers": {
        },
        "body": null,
        "method": "GET"
        })
        .then((response) => response.json())
        .then((data) => {
            if (data.totalItems !== 0 && !data.error) {
                setBooks(data.items);
                setIsDataRetrieved(true);
            }
            if (data.totalItems === 0) {
                setIsDataRetrieved(false);
                setNoResults('"'+searchTerm+'"'+" did not return any results");
            }
            
        })
        //is .catch((error) => error); needed here? what does it do? Reserach this
        // console.log(key);
        // console.log(searchTerm);
        // console.log(books.items[0].volumeInfo.title);
};

function handleNextClick(event) {
    event.preventDefault();
        setCurrentPage(currentPage+1);
}

function handleBackClick(event) {
    event.preventDefault();
        setCurrentPage(currentPage-1);
}

const onSearchFocus = (e) => {
    e.preventDefault();
    setSearchError('');
    setNoResults('');
    
}

//Need an onFocus function for search bar that clears error message and possibly "no results" message


    return(
        <body class="text-center bg">
            <div id="white-bg" class="container d-flex h-100 p-5 mx-auto flex-column align-items-top">
                <h2 style={{margin: "3rem"}}>Add a book to your personal collection:</h2>
                <div class="row d-flex align-items-center">
                        <form method="Get" onSubmit={handleSubmitSearch} class="mb-5">
                            <input type="text" name="searchTerm" value={searchTerm} placeholder='Search by title or author' class="form-control mb-2 w-25 mx-auto" onChange={handleInputChangeSearch} onFocus={onSearchFocus}/>
                            <input type="submit" class="btn btn-secondary mt-2" value="Search"/>
                        </form>
                        {
                            searchError ? <span style={{ color: 'red', fontSize: '16px'}}>{searchError}</span> : ''
                        }
                </div>
                <div class="mt-5 mx-auto">
                    {isDataRetrieved ? currentRecords.map(
                        book =>
                        <div style={{justifyContent: "left", display: "flex", flexDirection: "row"}}>
                            <p key={book.id}><img style={{width:100 ,height: 150}} src={book.volumeInfo.imageLinks ? book.volumeInfo.imageLinks.smallThumbnail : "https://upload.wikimedia.org/wikipedia/commons/6/65/No-Image-Placeholder.svg"} alt={book.volumeInfo.title}></img> <a class="link-secondary" href={book.volumeInfo.infoLink} target="_blank" rel="noreferrer noopener">{book.volumeInfo.title}</a> by {book.volumeInfo.authors} {book.volumeInfo.publishedDate ? book.volumeInfo.publishedDate.slice(0,4) : ""} 
                            </p>
                        </div>
                            ) : noResults
                    }
                    <span>
                        {isDataRetrieved && currentPage < 4 ? <button type="button" class="btn btn-secondary mx-1" onClick={handleNextClick}>Next</button> : ""}
                        {currentPage > 1 ? <button type="button" class="btn btn-secondary mx-1" onClick={handleBackClick}>Back</button> : ""}
                    </span>
                </div>
            </div>
        </body>
    )


}