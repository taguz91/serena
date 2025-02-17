<template>
  <NPageHeader
    title="Periodos Académicos"
    subtitle="Revisar, crear, editar y eliminar periodos académicos"
  >
    <template #extra>
      <NSpace>
        <NButton type="primary" @click="showModal">
          <template #icon>
            <NIcon>
              <Plus />
            </NIcon>
          </template>

          Crear nuevo
        </NButton>
      </NSpace>
    </template>

    <SearchInput @search="getSearch" />

    <NDataTable :columns="columns" :loading="isLoading" :data="academicPeriods" />

    <div class="mt-2 flex justify-end">
      <NPagination :page-count="metaData.pages" v-model:page="currentPage" @update:page="getPage" />
    </div>
  </NPageHeader>

  <FormView v-model="show" @update:model-value="toggleModal" :id="currentId" />
</template>

<script setup lang="ts">
import { Pencil, Plus, Trash } from '@vicons/tabler'
import {
  NBadge,
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
import { useAcademicPeriods } from '@/composable/academicPeriods/useAcademicPeriods'
import SearchInput from '@/components/basic/SearchInput.vue'

const show = ref(false)
const currentId = ref<string | undefined>(undefined)

const showModal = () => {
  show.value = true
}

const {
  isLoading,
  academicPeriods,
  metaData,
  currentPage,
  getPage,
  deleteAcademicPeriod,
  getSearch
} = useAcademicPeriods()

const toggleModal = (newShow: boolean) => {
  show.value = newShow
}

const columns: DataTableColumns<AcademicPeriod> = [
  {
    title: 'Carrera',
    key: 'carrera.name'
  },
  {
    title: 'Nombre',
    key: 'name'
  },
  {
    title: 'Estado',
    key: 'isActive',
    render(row) {
      return h(
        NBadge,
        {
          type: row.isActive ? 'success' : 'info',
          value: row.isActive ? 'Activo' : 'Inactivo'
        },
        {}
      )
    }
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
              deleteAcademicPeriod(row.id)
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
