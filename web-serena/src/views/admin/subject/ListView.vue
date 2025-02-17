<template>
  <NPageHeader title="Materias" subtitle="Revisar, crear, editar y eliminar materias">
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

    <SearchInput @search="getSearch" />

    <NDataTable :columns="columns" :data="subjects" :loading="isLoading" />

    <div class="mt-2 flex justify-end">
      <NPagination :page-count="metaData.pages" v-model:page="currentPage" @update:page="getPage" />
    </div>
  </NPageHeader>

  <FormView
    v-model="show"
    @update:model-value="(value: boolean) => (show = value)"
    :id="currentId"
  />
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
import type { Subject } from '@/interfaces'
import { useSubjects } from '@/composable/subjects/useSubjects'
import SearchInput from '@/components/basic/SearchInput.vue'

const show = ref(false)
const currentId = ref<string | undefined>(undefined)

const showModal = () => {
  show.value = true
}

const { subjects, isLoading, currentPage, metaData, deleteSubject, getPage, getSearch } =
  useSubjects()

const columns: DataTableColumns<Subject> = [
  {
    title: 'Nombre',
    key: 'name'
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
              deleteSubject(row.id)
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
