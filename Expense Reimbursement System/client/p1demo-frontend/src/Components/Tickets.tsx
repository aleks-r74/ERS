import axios, { AxiosError } from "axios";
import React, { useEffect, useRef, useState } from "react"
import { Container, Form, Table } from "react-bootstrap";
import { TicketDTO } from "../Interfaces/TicketDTO";
import { RmbUpdDTO } from "../Interfaces/RmbUpdDTO";
import { Link } from "react-router-dom";


export const Tickets:React.FC =()=>{
    const [infoMessage, setInfoMessage] = useState<string|undefined>();
    const [tickets, setTickets] = useState<TicketDTO[]>([]);
    const [ticketsState, setTicketsState] = useState<RmbUpdDTO[]>([]);
    const authObj = localStorage.getItem("authObject")
    const user = authObj != null ? JSON.parse(authObj) : {username: '', role: ''}
    
    useEffect(() => loadData(), []); // Empty dependency array to run this effect only once
    
    function loadData(){
        axios.get<TicketDTO[]>('http://localhost:8080/rmbs',{withCredentials: true})
        .then((response) => {
          setTicketsState(response.data.map(i=>({
            reimbId: i.reimbId,
            description: i.description,
            status: i.status
          })))
          setTickets(response.data)
        })
        .catch((err: AxiosError) => {
          const message = (err.response?.data as { message: string }).message;
          setInfoMessage(message ? message : undefined)
        });
    }

    function handleInputChange(index: number, field: keyof RmbUpdDTO, value: string) {
      const updatedTickets = [...ticketsState];
      updatedTickets[index] = {
        ...updatedTickets[index],   // Spread the existing properties of the ticket
        [field]: value,             // Dynamically set the field with the new value
      };
      setTicketsState(updatedTickets);
    }
    
    function updateTicket(index: number){
      const rmbUpdate: RmbUpdDTO = {
        reimbId: ticketsState[index].reimbId,
        description: ticketsState[index].description,
        status: ticketsState[index].status
      }
      axios.patch<RmbUpdDTO[]>('http://localhost:8080/rmbs',rmbUpdate, {withCredentials: true})
        .then(() =>{
          setInfoMessage("Updated at " + new Date().toISOString().substring(11,19))
          loadData()
        } )
        .catch((err: AxiosError) => {
          const message = (err.response?.data as { message: string }).message;
          setInfoMessage(message ? message : undefined)
        });
    }

    return(
    <>
    {user.role == "MANAGER" ? <Link to="/register"><button>New User</button></Link> : ""}
    <Link to="/newTicket"><button>New Ticket</button></Link>
    <Link to="/users"><button>Users</button></Link>
    <Link to="/logout"><button>Logout</button></Link>
    <h3>Welcome {user.username}, you're a {user.role}</h3>
        <p>{infoMessage}</p>
        <Container>
        <h4>Tickets</h4>
        <Table>
            <thead>
                <tr>
                    <th>User</th>
                    <th>Ticket #</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
        <tbody>
          {
            tickets.map((item, index)=>(
              <tr key={index}>
                <td className="align-middle">{item.user?.username}</td>
                <td className="align-middle">{item.reimbId}</td>
                <td className="align-middle">
                  <input type="text" 
                  value={ticketsState[index].description} 
                  onChange={(e) => handleInputChange(index, 'description', e.target.value)} 
                  />
                </td>
                <td className="align-middle">
                  <Form.Control as="select" 
                  value={ticketsState[index].status}
                  onChange={(e) => handleInputChange(index, 'status', e.target.value)} 
                >
                    <option>PENDING</option>
                    <option>DENIED</option>
                    <option>APPROVED</option>
                  </Form.Control>
                </td>
                <td className="align-middle">
                  <button onClick={()=>updateTicket(index)}>Update</button>
                </td>
              </tr>
            ))
          }
        </tbody>
        </Table>
        </Container>
    </>
    )
}
