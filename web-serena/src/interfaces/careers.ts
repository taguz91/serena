export interface Career {
  createdAt: string
  updatedAt: string
  createdBy: string
  updatedBy: any
  id: string
  name: string
  description: string
}

export interface CareerForm {
  id?: string
  name: string
  description?: string
}
