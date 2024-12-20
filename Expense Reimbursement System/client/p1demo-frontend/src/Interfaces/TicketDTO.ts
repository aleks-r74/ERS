export interface TicketDTO{
        reimbId: number
        description: string
        amount: number
        status: "PENDING" | "DENIED" | "APPROVED",
        user?: {
            userId:   number
            username: string
            role: "MANAGER" | "EMPLOYEE"
        }
}