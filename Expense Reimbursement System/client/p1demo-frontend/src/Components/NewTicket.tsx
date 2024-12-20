import axios, { AxiosError } from "axios";
import React, { useRef, useState } from "react"
import { Container } from "react-bootstrap";
import { TicketDTO } from "../Interfaces/TicketDTO";
import { Link, useNavigate } from "react-router-dom";



export const NewTicket:React.FC =()=>{
    const [infoMessage, setInfoMessage] = useState<string|undefined>();
    const authObj = localStorage.getItem("authObject")
    const user = authObj != null ? JSON.parse(authObj) : {username: '', role: ''}
    const amountRef = useRef<HTMLInputElement>(null);
    const descriptionRef = useRef<HTMLInputElement>(null);
    const navigate = useNavigate();
    
    function onSubmitClick(){
        console.log('clicked')
        const amount = amountRef.current?.value
        const description = descriptionRef.current?.value
      axios.post<void>('http://localhost:8080/rmbs',{amount, description}, {withCredentials: true})
        .then(() =>{
          setInfoMessage("Created at " + new Date().toISOString().substring(11,19))
          setTimeout(()=>navigate("/tickets"),500);
        } )
        .catch((err: AxiosError) => {
          const message = (err.response?.data as { message: string }).message;
          setInfoMessage(message ? message : undefined)
        });
    }

    return(
    <>
    {user.role == "MANAGER" ? <Link to="/register"><button>New User</button></Link> : ""}
    <Link to="/tickets"><button>Tickets</button></Link>
    <Link to="/users"><button>Users</button></Link>
    <Link to="/logout"><button>Logout</button></Link>
    <h3>Create a new ticket</h3>
        <p>{infoMessage}</p>
        <Container>
        <input type="number" id="amount" ref={amountRef}/>
        <input type="text" id="description" ref={descriptionRef}/>
        <button onClick={onSubmitClick}>Submit</button>
        </Container>
    </>
    )
}
