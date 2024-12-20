import axios, { AxiosError } from "axios";
import React, { useEffect, useState } from "react"
import { Container, Table } from "react-bootstrap";
import { UserDTO } from "../Interfaces/UserDTO";
import { Link } from "react-router-dom";


export const Users:React.FC =()=>{
    const [infoMessage, setInfoMessage] = useState<string|undefined>();
    const [usersState, setUsersState] = useState<UserDTO[]>([]);
    const authObj = localStorage.getItem("authObject")
    const user = authObj != null ? JSON.parse(authObj) : {username: '', role: ''}

    useEffect(() => loadData(), []); // Empty dependency array to run this effect only once
    
    function loadData(){
        axios.get<UserDTO[]>('http://localhost:8080/users',{withCredentials: true})
        .then((response) => {
            console.log(response)
          setUsersState(response.data)
        })
        .catch((err: AxiosError) => {
          const message = (err.response?.data as { message: string }).message;
          setInfoMessage(message ? message : undefined)
        });
    }
    const changeUserRole = (index: number) => {
        const user2Promote = usersState[index];
        axios.patch<UserDTO>(
            'http://localhost:8080/users',
            {
            username: user2Promote.username,
            role: user2Promote.role == "EMPLOYEE" ? "MANAGER" : "EMPLOYEE"
            },
            {withCredentials: true}
        ).then(()=>{
            loadData();
            setInfoMessage("Role updated")
        })
    };

    const handleDelete = (index: number) => {
        const user2Delete = usersState[index];
        axios.delete<void>(
            `http://localhost:8080/users/${user2Delete.userId}`, {withCredentials: true}
        ).then(()=>{
            loadData();
            setInfoMessage(`User ${user2Delete.username} and all their tickets deleted`)
        })
    };

    return(
    <>
        {user.role == "MANAGER" ? <Link to="/register"><button>New User</button></Link> : ""}
        <Link to="/newTicket"><button>New Ticket</button></Link>
        <Link to="/tickets"><button>Tickets</button></Link>
        <Link to="/logout"><button>Logout</button></Link>
        <p>{infoMessage}</p>
        <Container>
        <h3>Users</h3>
        <Table>
            <thead>
                <tr>
                    <th>User Id</th>
                    <th>Username</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Role</th>
                    {user.role=="MANAGER"? (<><th>Open Tickets</th><th>Action</th></>):""}
                </tr>
            </thead>
        <tbody>
          {
            usersState.map((item, index)=>(
              <tr key={index} className="bg-success">
                <td className="align-middle">{item.userId}</td>
                <td className="align-middle">{item.username}</td>
                <td className="align-middle">{item.firstName}</td>
                <td className="align-middle">{item.lastName}</td>
                <td className={item.role=="MANAGER" ? "align-middle text-primary" : "align-middle"}>{item.role}</td>
                {user.role=="MANAGER" ? 
                <>
                <td className="align-middle">{item.rmbList?.filter(ticket=>ticket.status=="PENDING").length}</td>
                <td className="align-middle">
                    {item.role == "EMPLOYEE" && <button onClick={()=>changeUserRole(index)} className="bg-success btn btn-small">Promote</button>}
                    {item.role == "MANAGER" && <button onClick={()=>changeUserRole(index)} className="bg-warning btn btn-small">Denote</button>}
                    <button onClick={()=>handleDelete(index)} className="bg-danger btn btn-small">Delete</button>
                </td>
                </>
                : ""}
              </tr>
            ))
          }
        </tbody>
        </Table>
        </Container>
    </>
    )
}
