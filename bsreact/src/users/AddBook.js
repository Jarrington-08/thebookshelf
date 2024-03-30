import React, { useState } from 'react';

export default function AddBook() {

const userId = window.sessionStorage.getItem("userId");
const [books, setBooks] = useState([]);
const [currentPage, setCurrentPage] = useState(1);
const [recordsPerPage] = useState(5);
const indexOfLastRecord = currentPage * recordsPerPage;
const indexOfFirstRecord = indexOfLastRecord - recordsPerPage;
const currentRecords = books ? books.slice(indexOfFirstRecord, indexOfLastRecord) : null;
const [isDataRetrieved, setIsDataRetrieved] = useState(false);
const [author, setAuthor] = useState('');
const [title, setTitle] = useState('');

//Do I need to make this a book object?
//Or would I select one book object from array of books?
// const [book, setBook] = useState('');
const [searchTerm, setSearchTerm] = useState('');
// const [searchError, setSearchError] = useState('');
const [noResults, setNoResults] = useState('');

function handleAddBook(title, authors, isbn, yearPublished, coverUrl) {
    fetch("http://localhost:8080/addUserCopy/"+window.sessionStorage.getItem("userId"), {
            method: "POST",
            headers: {
                "content-type": "application/json"
            },
            body: JSON.stringify({
                title,
                authors,
                isbn,
                yearPublished,
                coverUrl
            }),
        })
}

const handleInputChangeSearch = (e) => {
    setSearchTerm(e.target.value);
}

const handleInputChangeAuthor = (e) => {
    setAuthor(e.target.value);
}

const handleInputChangeTitle = (e) => {
    setTitle(e.target.value);
}

function handleSubmitSearch(event) {
            if (searchTerm === "") {
                event.preventDefault();
            }
            if (title) {
                setTitle("intitle:"+title);
                title.toString();
            }
            if (author) {
                setTitle("inauthor:"+author);
                author.toString();
            }
            searchTerm.toString();
            event.preventDefault();
            fetch("https://content-books.googleapis.com/books/v1/volumes?q="+searchTerm+title+author+"&maxResults=20", {
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
                setCurrentPage(1);
            }
            if (data.totalItems === 0) {
                setIsDataRetrieved(false);
                setNoResults("Your search did not return any results");
            }
            
        })
        //is .catch((error) => error); needed here? what does it do? Reserach this
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
    setNoResults('');
    
}

const onAuthorFocus = (e) => {
    e.preventDefault();
    setAuthor('');
    
}

const onTitleFocus = (e) => {
    e.preventDefault();
    setTitle('');
    
}


    return(
        <body class="text-center bg">
            <div id="white-bg" class="container d-flex h-100 p-5 mx-auto flex-column align-items-top">
                <h2 style={{margin: "3rem"}}>Add a book to your personal collection:</h2>
                <div class="row d-flex align-items-center">
                        <form method="Get" onSubmit={handleSubmitSearch} class="mb-5">
                            <input type="text" name="searchTerm" placeholder='Search' class="form-control mb-2 w-25 mx-auto" onChange={handleInputChangeSearch} onFocus={onSearchFocus}/>
                            <div>
                                Need to refine your search? Try adding a title or author to your search query.
                                <input type="text" name="author" placeholder='Search by title' class="form-control mb-2 w-25 mx-auto" onChange={handleInputChangeTitle} onFocus={onTitleFocus}/>
                                <input type="text" name="title" placeholder='Search by author' class="form-control mb-2 w-25 mx-auto" onChange={handleInputChangeAuthor} onFocus={onAuthorFocus}/>
                            </div>
                            <input type="submit" class="btn btn-secondary mt-2" value="Search"/>
                        </form>
                        
                </div>
                <div class="mt-5 mx-auto">
                    {isDataRetrieved ? currentRecords.map(
                        book =>
                        <div style={{justifyContent: "left", display: "flex", flexDirection: "row"}}>
                            <p key={book.id}><img style={{width:100 ,height: 150, marginRight: "2em"}} src={book.volumeInfo.imageLinks ? book.volumeInfo.imageLinks.smallThumbnail : "https://upload.wikimedia.org/wikipedia/commons/6/65/No-Image-Placeholder.svg"} alt={book.volumeInfo.title}></img> 
                            <a class="link-secondary" href={book.volumeInfo.infoLink} target="_blank" rel="noreferrer noopener">{book.volumeInfo.title}</a> by {book.volumeInfo.authors ? book.volumeInfo.authors.map(author => <span>{author}, </span>) : "Unknown Author,"} {book.volumeInfo.publishedDate ? book.volumeInfo.publishedDate.slice(0,4) :
                            //For POST request how do we handle missing data? Only in backend? Or do we need to create filler data in front end before sending? ***I could use default values for function parameters in REACT***
                            "Publication Year Unavailable"} <button class="btn btn-secondary" onClick={() => { handleAddBook(book.volumeInfo.title, book.volumeInfo.authors, book.volumeInfo.industryIdentifiers[0].identifier, book.volumeInfo.publishedDate.slice(0,4), book.volumeInfo.imageLinks.smallThumbnail)}}>Add</button> 
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