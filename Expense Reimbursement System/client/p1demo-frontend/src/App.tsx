import './App.css'
import 'bootstrap/dist/css/bootstrap.css'

import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { Login } from './Components/LoginRegister/Login'
import { Register } from './Components/LoginRegister/Register'
import { Tickets } from './Components/Tickets'
import { Logout } from './Components/LoginRegister/Logout'
import { Users } from './Components/Users'
import { NewTicket } from './Components/NewTicket'

function App() {

  return (
    <>
        <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login/>} />
          <Route path="/register" element={<Register/>} />
          <Route path="/tickets" element={<Tickets/>} />
          <Route path="/users" element={<Users/>} />
          <Route path="/logout" element={<Logout/>} />
          <Route path="/newTicket" element={<NewTicket/>} />
        </Routes>
        </BrowserRouter>
    </>
  )
}

export default App
