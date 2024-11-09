<template>
  <NPageHeader title="Clases" subtitle="Revisar, crear, editar y eliminar clases">
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

    <NDataTable :columns="columns" :data="classrooms" :loading="isLoading" />

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
import type { Classroom } from '@/interfaces'
import { useClassrooms } from '@/composable/classrooms/useClassrooms'

const show = ref(false)
const currentId = ref<string | undefined>(undefined)

const showModal = () => {
  show.value = true
}

const { classrooms, metaData, currentPage, isLoading, getPage, deleteClassroom } = useClassrooms()

const columns: DataTableColumns<Classroom> = [
  {
    title: 'Periodo académico',
    key: 'academicPeriod.name'
  },
  {
    title: 'Profesor',
    key: 'teacher.name'
  },
  {
    title: 'Materia',
    key: 'subject.name'
  },
  {
    title: 'Curso',
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
              deleteClassroom(row.id)
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
