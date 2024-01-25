import React, { useState } from 'react'
import { useNavigate } from "react-router-dom";

export default function Profile() {

    let navigate = useNavigate();

    const userId = window.sessionStorage.getItem("userId");
    const [passwordError, setPasswordError] = useState('');
    const [verifyPasswordError, setVerifyPasswordError] = useState('');

        const onSubmit = (e) => {
            e.preventDefault();
       
            const formData = new FormData(e.target);
    
            if(formData.get('password') !== formData.get('verifyPassword')) {
              setVerifyPasswordError('Passwords do not match.');
              e.preventDefault();
            } else {
    
            fetch("http://localhost:8080/changePassword", {
              method: "PUT",
              headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
              },
              body: JSON.stringify({
                password: formData.get('password'),
                userId: userId 
              }),
            })
              .then((response) => response.json())
              .then((data) => {
                  if (data.fieldErrors) {
                    data.fieldErrors.forEach(fieldError => {
                      if(fieldError.field === 'password'){
                        setPasswordError(fieldError.message);
                      }
                    });
                } else {
                  alert("You have succesfully changed your password.");
                  navigate("/editprofile");
                  return navigate(0);
                }
              })
              .catch((err) => err);
           }
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
          <form method="PUT" autoComplete="off" onSubmit={onSubmit}>
              <div class="form-outline p-5 m-5">
                <h1>Change Your Password Below</h1>
              </div>
              <div class="form-outline mb-4">
                <input type="password" name="password" class="form-control" placeholder="Password" onFocus={onPasswordFocus}/>
                  {
                    passwordError ? <span style={{ color: 'red', fontSize: '12px'}}>{passwordError}</span> : ''
                  }
              </div>
              <div class="form-outline mb-4">
              <input type="password" name="verifyPassword" class="form-control" placeholder="Verify password" onFocus={onVerifyPasswordFocus}/>
                        {
                        verifyPasswordError ? <span style={{ color: 'red', fontSize: '12px'}}>{verifyPasswordError}</span> : ''
                        }
              </div>
              <div>
                <input type="submit" name="submit" value="Sign in" class="btn btn-primary btn-block mb-4"/>
              </div>
          </form>
        </div>
  </body>
    );
}

        
            