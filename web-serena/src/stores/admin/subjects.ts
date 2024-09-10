import { ref } from 'vue'

import { defineStore } from 'pinia'

import type { Meta, Subject } from '@/interfaces'
import { defaultMeta } from '@/utils'

export const useSubjectStore = defineStore('subject', () => {
  const metaData = ref<Meta>(defaultMeta())
  const currentPage = ref(1)

  const subjects = ref<Subject[]>([])

  return {
    metaData,
    currentPage,
    subjects,

    // actions

    setSubjects(newSubjects: Subject[]) {
      subjects.value = newSubjects
    },
    setMeta(meta: Meta) {
      metaData.value = { ...meta }
    },
    setPage(page: number) {
      if (page == currentPage.value) {
        return
      }

      currentPage.value = page
    }
  }
})
