import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Navigate } from "react-router-dom";

export default function Login() {

    let navigate = useNavigate();

    const [emailError, setEmailError] = useState('');
    const [passwordError, setPasswordError] = useState('');

    if (window.sessionStorage.getItem('loggedIn') === "true") {
      return <Navigate replace to="/profile" />
    }
    
    const onSubmit = (e) => {
        e.preventDefault();
    
        const formData = new FormData(e.target);
    
        fetch("http://localhost:8080/login", {
          method: "POST",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            email: formData.get('email'),
            password: formData.get('password'),
          }),
        })
          .then((response) => response.json())
          .then((data) => {
            if(data.message === "Invalid email or password" || data.message === "Please click the verification link in your email.") {
              alert(data.message);
            } else if(data.fieldErrors) {
              data.fieldErrors.forEach(fieldError => {
                if(fieldError.field === 'email'){
                  setEmailError(fieldError.message);
                }
    
                if(fieldError.field === 'password'){
                  setPasswordError(fieldError.message);
                }
              });
            } else {
              sessionStorage.setItem("userId", data.userId);
              sessionStorage.setItem("loggedIn", "true");
              navigate("/profile");
              return navigate(0);
            }
          })
          .catch((err) => err);
      }

      const onEmailFocus = (e) => {
        e.preventDefault();
        setEmailError('');
      }
    
      const onPasswordFocus = (e) => {
        e.preventDefault();
        setPasswordError('');
      }

      return (
        <body class="text-center bg">
            <div id="white-bg" class="container min-vh-100 p-5 d-flex justify-content-center align-items-top">
            <form method="POST" autoComplete="off" onSubmit={onSubmit}>
                <div class="form-outline p-5 m-5">
                  <h1>Sign In Here</h1>
                </div>
                <div class="form-outline mb-4">
                  <input type="email" name="email" class="form-control" placeholder="Email address" onFocus={onEmailFocus}/>
                  {
                      emailError ? <span style={{ color: 'red', fontSize: '12px'}}>{emailError}</span> : ''
                  }
                </div>
                <div class="form-outline mb-4">
                  <input type="password" name="password" class="form-control" placeholder="Password" onFocus={onPasswordFocus}/>
                    {
                      passwordError ? <span style={{ color: 'red', fontSize: '12px'}}>{passwordError}</span> : ''
                    }
                </div>
                  <div>
                    <input type="submit" name="submit" value="Sign in" class="btn btn-primary btn-block mb-4"/>
                  </div>
                  <p>
                    Not a member of the BookShelf Corner? Sign up <a href="/register">here!</a>
                  </p>
            </form>
            </div>
          </body>
      )

}