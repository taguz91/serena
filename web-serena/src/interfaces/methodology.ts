export interface Methodology {
  createdAt: string
  updatedAt: string
  createdBy: string
  updatedBy?: string
  id: string
  name: string
  summary: string
  emotions: MethodologyEmotion[]
}

export interface MethodologyEmotion {
  id: string
  emotion: string
}

export interface MethodologyForm {
  id?: string
  name: string
  summary: string
  emotions: string[]
}
