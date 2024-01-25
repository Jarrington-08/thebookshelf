import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from './users/Login';
import RegisterUser from './users/Register';
import Profile from './users/Profile';
import Home from './components/Home';
import PrivateRoute from './users/PrivateRoute';

import './App.css';
import Event from './components/Events.js';
import BookShelf from './components/BookShelf.js';


import CreateEvent from './components/CreateEvent';
import Events from './components/Events';
import Reviews from './components/Reviews';


import EditProfile from './users/EditProfile';
import ChangePassword from './users/ChangePassword';
import NavBar from './components/NavBar';





function App() {
  return (
    <div>
       
      
      <Router>
      <NavBar />
        <Routes>
          <Route exact path="/" element={<Home />}></Route>
          <Route exact path="/login" element={<Login />}></Route>
          <Route exact path="/register" element={<RegisterUser />}></Route>
          <Route path="/profile" element={
            <PrivateRoute>
              <Profile />
            </PrivateRoute>
          }/>
          <Route exact path='/events' element={<Event/>}></Route>
          <Route exact path='/bookshelf'element={<BookShelf/>}></Route>
          <Route exact path='/createevent' element={<CreateEvent/>}></Route>
          <Route exact path='/events' element={<Events/>}></Route>
          <Route exact path='/reviews' element={<Reviews/>}></Route>
          <Route path="/editprofile" element={
            <PrivateRoute>
              <EditProfile />
            </PrivateRoute>
          }/>
          <Route path="/changepassword" element={
            <PrivateRoute>
              <ChangePassword />
            </PrivateRoute>
           }/>
        </Routes>
      </Router>
    </div>
    
    
  );
}


export default App;