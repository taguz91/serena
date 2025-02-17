import { ref } from 'vue'

import { defineStore } from 'pinia'

import type { Meta, Teacher } from '@/interfaces'
import { defaultMeta } from '@/utils'

export const useTeachersStore = defineStore('teachers', () => {
  const metaData = ref<Meta>(defaultMeta())
  const currentPage = ref(1)
  const search = ref<string | undefined>()

  const teachers = ref<Teacher[]>([])

  return {
    // states
    metaData,
    teachers,
    currentPage,
    search,

    // actions
    setTeachers(newTeachers: Teacher[]) {
      teachers.value = newTeachers
    },

    setMeta(newMeta: Meta) {
      metaData.value = {
        ...newMeta
      }
    },

    setPage(page: number) {
      if (page === currentPage.value) {
        return
      }

      currentPage.value = page
    },
    setSearch(newSearch: string | undefined) {
      search.value = newSearch
    }
  }
})
