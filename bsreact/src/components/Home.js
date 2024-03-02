import './../App.css';

export default function Home() {

    return (
            <body class="text-center bg">
                 <div id="white-bg" class="container d-flex h-100 p-5 mx-auto flex-column align-items-top">
                    <main role="main" class="">
                        <h1 class="cover-heading p-2 m-5">Welcome to <br />
                        The BookShelf</h1><br />
                        <p class="lead" style={{fontSize: "1.5em"}} ><span><b>A community of readers who:<br /></b></span>Share favorite books<br />
                        Discover new titles<br />Build community</p><br />
                        <p class="lead">
                            <a href="/register" class="btn btn-lg btn-secondary">Join the Community</a>
                            <br /></p>
                            <p class="lead">And help promote sustainable consumption</p>
                    </main>
            </div>
            <footer>Made by John Arrington</footer>
        </body>
    )
}
