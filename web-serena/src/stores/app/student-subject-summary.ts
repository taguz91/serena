import { ref } from 'vue'
import { defineStore } from 'pinia'

import type { StudentSubject } from '@/interfaces'

export const useStudentSubjectSummary = defineStore('student-subject', () => {
  const subjects = ref<StudentSubject[]>([])

  return {
    subjects,

    setSubjects(newSubjects: StudentSubject[]) {
      subjects.value = newSubjects
    }
  }
})
