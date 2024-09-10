import { defineStore } from 'pinia'
import { ref } from 'vue'

import type { AcademicPeriod, Meta } from '@/interfaces'
import { defaultMeta } from '@/utils'

export const useAcademicPeriodsStore = defineStore('academicPeriods', () => {
  const metaData = ref<Meta>(defaultMeta())
  const currentPage = ref(1)

  const academicPeriods = ref<AcademicPeriod[]>([])

  return {
    metaData,
    academicPeriods,
    currentPage,

    // actions
    setAcademicPeriods(newAcademicPeriods: AcademicPeriod[]) {
      academicPeriods.value = newAcademicPeriods
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
