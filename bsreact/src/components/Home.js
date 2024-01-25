import './../App.css';

export default function Home() {

    return (
            <body class="text-center bg">
                 <div id="white-bg" class="container d-flex h-100 p-5 mx-auto flex-column align-items-top">
                    <main role="main" class="">
                        <h1 class="cover-heading p-5 m-5">Welcome to the BookShelf Corner</h1><br />
                        <p class="lead"><span><b>A community of readers who:<br /></b></span>Share favorite books<br />
                        Discover new titles<br /> Engage in a collaborative literary experience</p><br />
                        <p class="lead">
                            <a href="/register" class="btn btn-lg btn-secondary">Join the Commmunity</a>
                            <br /></p>
                            <p class="lead">And help promote sustainable consumption</p>
                    </main>
            </div>
            <footer>Made by the Four A's</footer>
        </body>
    )
}
