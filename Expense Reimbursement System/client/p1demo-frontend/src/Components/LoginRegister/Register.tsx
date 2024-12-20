import axios, { AxiosError } from "axios";
import React, { useRef, useState } from "react"
import { useNavigate } from "react-router-dom";
import { UserDTO } from "../../Interfaces/UserDTO";

export const Register:React.FC =()=>{
    const navigate = useNavigate();
    const [infoMessage, setInfoMessage] = useState<string|undefined>();
    const authObj = localStorage.getItem("authObject")
    const user = authObj != null ? JSON.parse(authObj) : {username: '', role: ''}
    const usernameRef = useRef<HTMLInputElement>(null);
    const passwordRef = useRef<HTMLInputElement>(null);
    const firstNameRef = useRef<HTMLInputElement>(null);
    const lastNameRef = useRef<HTMLInputElement>(null);
    const roleRef = useRef<HTMLSelectElement>(null);

    const onSubmitClick = ()=>{
        if(usernameRef.current?.value.trim() 
            && passwordRef.current?.value.trim()
            && firstNameRef.current?.value.trim()
            && lastNameRef.current?.value.trim()
        ){
            let userInfo: UserDTO = {
            username:   usernameRef.current?.value.trim(),
            password:   passwordRef.current?.value.trim(),
            firstName:  firstNameRef.current?.value.trim(),
            lastName:   lastNameRef.current?.value.trim(),
            role:       roleRef.current?.value as "EMPLOYEE" | "MANAGER"
            }
            sendRequest(userInfo)
        }
        else
          setInfoMessage("All fields must be set")
    }

    function sendRequest(user: UserDTO):void {
        axios.post('http://localhost:8080/users',user,{withCredentials: true})
          .then((response) => {
           setInfoMessage(`user ${usernameRef.current?.value!} created`)
           if(user.role!="MANAGER") localStorage.setItem("authObject",JSON.stringify(response.data))
           setTimeout(()=>navigate("/tickets"),2000)

          })
          .catch((err: AxiosError) => {
            const message = (err.response?.data as { message: string })?.message;
            setInfoMessage(message ? message : undefined)
          });
    } 
    
    return(
    <>
    <div>{infoMessage}</div>
    <div>
      <input type="text" id="username" placeholder="Username" ref={usernameRef}/>
    </div>
    <div>
      <input type="text" id="password" placeholder="Password" ref={passwordRef}/>
    </div>
    <div>
      <input type="text" id="firstName" placeholder="First Name" ref={firstNameRef}/>
    </div>
    <div>
      <input type="text" id="lastName" placeholder="Last Name" ref={lastNameRef}/>
    </div>
    <div>
        <select ref={roleRef}>
            <option selected>EMPLOYEE</option>
            <option>MANAGER</option>
        </select>
    </div>
    <div className="d-flex justify-content-center">
        <button onClick={onSubmitClick}>Submit</button>
    </div>
    </>
    )
}