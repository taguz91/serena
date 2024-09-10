import type { Classroom } from '@/interfaces'
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useCurrentClassroomsStore = defineStore('current-classrooms', () => {
  const classrooms = ref<Classroom[]>()

  return {
    classrooms,

    setClassrooms(newClassrooms: Classroom[]) {
      classrooms.value = newClassrooms
    }
  }
})
