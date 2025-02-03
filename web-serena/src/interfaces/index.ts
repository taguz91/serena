export * from './shared'

// models
export * from './teacher'
export * from './academicPeriods'
export * from './subject'
export * from './classrooms'
export * from './register'
export * from './student'
export * from './careers'
export * from './methodology'

export interface ProfileForm {
  name?: string
  email?: string
  password?: string
}
