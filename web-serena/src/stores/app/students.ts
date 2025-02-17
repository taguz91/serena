import type { Meta, Student } from '@/interfaces'
import { defaultMeta } from '@/utils'
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useStudentStore = defineStore('students', () => {
  const metaData = ref<Meta>(defaultMeta())
  const currentPage = ref(1)
  const search = ref<string | undefined>()

  const students = ref<Student[]>([])

  return {
    students,
    metaData,
    currentPage,
    search,
    // actions

    setStudents(newStudents: Student[]) {
      students.value = newStudents
    },
    setMeta(meta: Meta) {
      metaData.value = { ...meta }
    },
    setPage(page: number) {
      if (page == currentPage.value) {
        return
      }

      currentPage.value = page
    },
    setSearch(newSearch: string | undefined) {
      search.value = newSearch
    }
  }
})
