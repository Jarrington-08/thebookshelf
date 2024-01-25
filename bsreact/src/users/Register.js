import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import { Navigate } from "react-router-dom";

export default function RegisterUser() {

    let navigate = useNavigate();

    const [emailError, setEmailError] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [usernameError, setUsernameError] = useState('');
    const [verifyPasswordError, setVerifyPasswordError] = useState('');

    if (window.sessionStorage.getItem('loggedIn') === "true") {
      return <Navigate replace to="/profile" />
    }

    const onSubmit = (e) => {
        e.preventDefault();
   
        const formData = new FormData(e.target);

        if(formData.get('password') !== formData.get('verifyPassword')) {
          setVerifyPasswordError('Passwords do not match.');
          e.preventDefault();
        } else {

        fetch("http://localhost:8080/register", {
          method: "POST",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            email: formData.get('email'),
            username: formData.get('username'),
            password: formData.get('password')
          }),
        })
          .then((response) => response.json())
          .then((data) => {
              if(data.message === "Username unavailable" || data.message === "Email already registered") {
                alert(data.message);
              } else if (data.fieldErrors) {
                data.fieldErrors.forEach(fieldError => {
                  if(fieldError.field === 'email'){
                    setEmailError(fieldError.message);
                  }
                  if(fieldError.field === 'username'){
                      setUsernameError(fieldError.message);
                    }
                  if(fieldError.field === 'password'){
                    setPasswordError(fieldError.message);
                  }
                });
            } else {
              alert("You have succesfully registered. A verification email has been sent. Please click on the link to verify your acccount.");
              sessionStorage.setItem("userId", data.userId);
              navigate("/login");
              return navigate(0);
            }
          })
          .catch((err) => err);
       }
      }
    

      const onEmailFocus = (e) => {
        e.preventDefault();
        setEmailError('');
      }

      const onUsernameFocus = (e) => {
        e.preventDefault();
        setUsernameError('');
      }
    
      const onPasswordFocus = (e) => {
        e.preventDefault();
        setPasswordError('');
      }

      const onVerifyPasswordFocus = (e) => {
        e.preventDefault();
        setVerifyPasswordError('');
      }
    
      return (
        <body class="text-center bg">
            <div id="white-bg" class="container min-vh-100 p-5 d-flex justify-content-center align-items-top">
              <form method="POST" autoComplete="off" onSubmit={onSubmit}>
                  <div class="form-outline p-5 m-5">
                    <h1>Register Here</h1>
                  </div>
                  <div class="form-outline mb-4">
                    <input type="email" name="email" id="form2Example1" class="form-control" placeholder="Email address" onFocus={onEmailFocus} />
                    {
                        emailError ? <span style={{ color: 'red', fontSize: '12px'}}>{emailError}</span> : ''
                    }
                  </div>
                  <div class="form-outline mb-4"> 
                    <input type="text" name="username" id="form2Example2" class="form-control" placeholder="Username" onFocus={onUsernameFocus} />
                    {
                        usernameError ? <span style={{ color: 'red', fontSize: '12px'}}>{usernameError}</span> : ''
                    }
                  </div>
                  <div class="form-outline mb-4">
                      <input type="password" name="password" id="form2Example3" class="form-control" placeholder="Password" onFocus={onPasswordFocus} />
                          {
                          passwordError ? <span style={{ color: 'red', fontSize: '12px'}}>{passwordError}</span> : ''
                          }
                  </div>
                  <div class="form-outline mb-4">
                      <input type="password" name="verifyPassword" id="form2Example4" class="form-control" placeholder="Verify password" onFocus={onVerifyPasswordFocus} />
                          {
                          verifyPasswordError ? <span style={{ color: 'red', fontSize: '12px'}}>{verifyPasswordError}</span> : ''
                          }
                  </div>
                  <div>
                      <input type="submit" name="submit" class="btn btn-primary btn-block mb-4" value="Sign up"/>
                  </div>
                  <p>
                    Already a member? Sign in <a href="/login">here!</a>
                  </p>
              </form>
            </div>
          </body>
      );
}
