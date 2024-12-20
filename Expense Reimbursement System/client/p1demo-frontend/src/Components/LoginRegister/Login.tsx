import axios, { AxiosError } from "axios";
import React, { useEffect, useRef, useState } from "react"
import { useNavigate } from "react-router-dom"
import { UserDTO } from "../../Interfaces/UserDTO";

export const Login:React.FC =()=>{
    const navigate = useNavigate();
    const [infoMessage, setInfoMessage] = useState<string|undefined>();
    const usernameRef = useRef<HTMLInputElement>(null);
    const passwordRef = useRef<HTMLInputElement>(null);

    const onRegisterClick = ()=>navigate("/register")

    const onLoginClick = ()=>{
      if(usernameRef.current?.value.trim() && passwordRef.current?.value.trim())
        login(usernameRef.current?.value!, passwordRef.current?.value!)
      else
        setInfoMessage("Enter username and password")
    }

    function login(username:string, password:string):void {

        axios.post<UserDTO>('http://localhost:8080/users/login',{username, password},{withCredentials: true})
          .then((response) => {
            localStorage.setItem("authObject",JSON.stringify(response.data))
            setInfoMessage(`You're authorized as ${response.data.username}`)
           setTimeout(()=>navigate("/tickets"),2000)
          })
          .catch((err: AxiosError) => {
            const message = (err.response?.data as { message: string })?.message;
            setInfoMessage(message ? message : undefined)
          });
      } 
      
    return(
    <>
    <p>{infoMessage}</p>
    <div>
      <input type="text" id="username" placeholder="Username" ref={usernameRef}/>
    </div>
    <div>
      <input type="password" id="password" placeholder="Password" ref={passwordRef}/>
    </div>
    <div className="d-flex justify-content-between gap-3">
      <button onClick={onRegisterClick}>Register</button>
      <button onClick={onLoginClick}>Login</button>
    </div>
    </>
    )
}