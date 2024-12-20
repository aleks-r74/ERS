import axios, { AxiosError } from "axios";
import React, { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"

export const Logout:React.FC =()=>{
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState<string|undefined>();

    useEffect(() => {
        // Making a GET request
        axios.get('http://localhost:8080/users/logout',{withCredentials: true})
          .then(() => {
            navigate("/")
            console.log("logged out")

          })
          .catch((err: AxiosError) => {
            const message = (err.response?.data as { message: string }).message;
            setErrorMessage(message ? message : undefined)
          });
      }, []); // Empty dependency array to run this effect only once
      
    return(
    <>
    <p>{errorMessage}</p>
    </>
    )
}