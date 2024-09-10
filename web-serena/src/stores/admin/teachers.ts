import type { Meta, Teacher } from '@/interfaces'
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useTeachersStore = defineStore('teachers', () => {
  const metaData = ref<Meta>({
    current: 0,
    items: 0,
    pages: 1,
    perPage: 20
  })

  const currentPage = ref(1)

  const teachers = ref<Teacher[]>([])

  return {
    // states
    metaData,
    teachers,
    currentPage,

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
    }
  }
})
