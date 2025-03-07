<template>
  <NPageHeader title="Carreras" subtitle="Revisar, crear, editar y eliminar carreras">
    <template #extra>
      <NSpace>
        <NButton type="primary" @click="showModal">
          <template #icon>
            <NIcon>
              <Plus />
            </NIcon>
          </template>

          Crear nueva
        </NButton>
      </NSpace>
    </template>

    <SearchInput :search="search" @search="getSearch" />

    <NDataTable :columns="columns" :loading="isLoading" :data="careers" />

    <div class="mt-2 flex justify-end">
      <NPagination :page-count="metaData.pages" v-model:page="currentPage" @update:page="getPage" />
    </div>
  </NPageHeader>

  <FormView v-model="show" @update:model-value="toggleModal" :id="currentId" />
</template>

<script setup lang="ts">
import { Pencil, Plus, Trash } from '@vicons/tabler'
import {
  NButton,
  NDataTable,
  NIcon,
  NPageHeader,
  NPagination,
  NSpace,
  type DataTableColumns
} from 'naive-ui'
import { h, ref } from 'vue'
import FormView from './FormView.vue'
import type { AcademicPeriod } from '@/interfaces'
import { useCareers } from '@/composable/careers/useCareers'
import SearchInput from '@/components/basic/SearchInput.vue'

const show = ref(false)
const currentId = ref<string | undefined>(undefined)

const showModal = () => {
  show.value = true
}

const { isLoading, careers, metaData, currentPage, getPage, deleteCareer, getSearch, search } =
  useCareers()

const toggleModal = (newShow: boolean) => {
  show.value = newShow
}

const columns: DataTableColumns<AcademicPeriod> = [
  {
    title: 'Nombre',
    key: 'name'
  },
  {
    title: 'Descripción',
    key: 'description'
  },
  {
    title: 'Acciones',
    key: 'actions',
    width: 125,
    className: 'flex justify-between',
    render: (row) => {
      return [
        h(
          NButton,
          {
            type: 'info',
            tertiary: true,
            onClick: () => {
              currentId.value = row.id
              showModal()
            }
          },
          {
            icon: () => h(NIcon, null, { default: () => h(Pencil) })
          }
        ),
        h(
          NButton,
          {
            type: 'error',
            tertiary: true,
            onClick: () => {
              deleteCareer(row.id)
            }
          },
          {
            icon: () => h(NIcon, null, { default: () => h(Trash) })
          }
        )
      ]
    }
  }
]
</script>
