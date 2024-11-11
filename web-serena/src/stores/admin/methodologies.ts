import { ref } from 'vue'
import { defineStore } from 'pinia'

import type { Meta, Methodology } from '@/interfaces'
import { defaultMeta } from '@/utils'

export const useMethodologyStore = defineStore('methodologies', () => {
  const metaData = ref(defaultMeta())
  const currentPage = ref(1)

  const methodologies = ref<Methodology[]>([])

  return {
    metaData,
    currentPage,
    methodologies,

    // actions
    setMethodologies(newMethodologies: Methodology[]) {
      methodologies.value = newMethodologies
    },
    setMeta(newMeta: Meta) {
      metaData.value = {
        ...newMeta
      }
    },
    setPage(page: number) {
      if (page == currentPage.value) {
        return
      }

      currentPage.value = page
    }
  }
})
