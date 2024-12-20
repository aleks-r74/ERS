import { TicketDTO } from "./TicketDTO"

export interface UserDTO{
    userId?: number,
    username: string
    password: string
    firstName: string
    lastName: string
    role?: "EMPLOYEE"|"MANAGER",
    rmbList?: TicketDTO[]
}