export interface RmbUpdDTO{
    reimbId: number;
    status?: "PENDING" | "DENIED" | "APPROVED"
    description?: string;
}