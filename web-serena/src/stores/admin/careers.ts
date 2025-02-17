import { ref } from 'vue'
import { defineStore } from 'pinia'

import type { Career, Meta } from '@/interfaces'
import { defaultMeta } from '@/utils'

export const useCareersStore = defineStore('careers', () => {
  const metaData = ref<Meta>(defaultMeta())
  const currentPage = ref(1)
  const search = ref<string | undefined>()

  const careers = ref<Career[]>([])

  return {
    metaData,
    careers,
    currentPage,
    search,

    setCareers(newCareers: Career[]) {
      careers.value = newCareers
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
