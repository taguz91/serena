import type { Meta, Student } from '@/interfaces'
import { defaultMeta } from '@/utils'
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useStudentClassroomStore = defineStore('students-classroom', () => {
  const metaData = ref<Meta>(defaultMeta())

  const students = ref<Student[]>([])

  return {
    students,
    metaData,
    // actions

    setStudents(newStudents: Student[]) {
      students.value = newStudents
    },
    setMeta(meta: Meta) {
      metaData.value = { ...meta }
    }
  }
})
