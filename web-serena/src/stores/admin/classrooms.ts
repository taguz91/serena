import type { Classroom } from '@/interfaces'
import { defaultMeta } from '@/utils'
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useClassroomsStore = defineStore('classrooms', () => {
  const metaData = ref(defaultMeta())
  const currentPage = ref(1)

  const classrooms = ref<Classroom[]>([])

  return {
    metaData,
    currentPage,
    classrooms,

    // actions

    setClassrooms(newClassrooms: Classroom[]) {
      classrooms.value = newClassrooms
    },

    setPage(page: number) {
      if (page == currentPage.value) {
        return
      }

      currentPage.value = page
    }
  }
})
