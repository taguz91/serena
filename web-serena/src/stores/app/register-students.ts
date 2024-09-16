import { ref } from 'vue'
import { defineStore } from 'pinia'

import type { RegisterStudent } from '@/interfaces'

export const useRegisterStudentsStore = defineStore('register-students', () => {
  const students = ref<RegisterStudent[]>([])

  return {
    students,

    setStudents(newStudents: RegisterStudent[]) {
      students.value = newStudents
    },
    addStudent(newStudent: RegisterStudent) {
      this.setStudents([...students.value, newStudent])
    }
  }
})
