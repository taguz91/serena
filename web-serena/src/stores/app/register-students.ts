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
      const exists = students.value.find((student) => student.id === newStudent.id)
      if (exists) return

      this.setStudents([...students.value, newStudent])
    }
  }
})
