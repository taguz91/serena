export interface Teacher {
  createdAt: string
  updatedAt: string
  createdBy: string | undefined
  updatedBy: string | undefined
  id: string
  reference: string | undefined
  name: string
  email: string
  isActive: boolean | undefined
  lastLogin: string | undefined
  token: string
}
