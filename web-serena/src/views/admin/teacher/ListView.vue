<template>
  <NPageHeader title="Docentes" subtitle="Revisar, crear, editar y eliminar docentes">
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

    <NDataTable :columns="columns" :data="teachers" :loading="isLoading" />

    <div class="mt-2 flex justify-end">
      <NPagination :page-count="metaData.pages" v-model:page="currentPage" @update:page="getPage" />
    </div>
  </NPageHeader>

  <FormView v-model="show" :id="currentId" @update:model-value="toggleModal" />
</template>

<script setup lang="ts">
import { h, ref } from 'vue'

import { LockOff, LockAccess, Pencil, Plus, Trash } from '@vicons/tabler'
import {
  NButton,
  NDataTable,
  NIcon,
  NPageHeader,
  NPagination,
  NSpace,
  type DataTableColumns
} from 'naive-ui'

import FormView from './FormView.vue'
import { useTeachers } from '@/composable/teachers/useTeachers'
import type { Teacher } from '@/interfaces'

const show = ref(false)
const currentId = ref<string | undefined>(undefined)

const showModal = () => {
  show.value = true
}

const { isLoading, teachers, metaData, currentPage, getPage, deleteTeacher } = useTeachers()

const toggleModal = (newShow: boolean) => {
  show.value = newShow
}

const columns: DataTableColumns<Teacher> = [
  {
    title: 'Nombre',
    key: 'name'
  },
  {
    title: 'Correo',
    key: 'email'
  },
  {
    title: 'Acciones',
    key: 'actions',
    width: 175,
    className: 'flex justify-between',
    render: (row) => {
      return [
        h(
          NButton,
          {
            type: 'info',
            tertiary: true,
            onClick: () => {
              console.log('info', row)
            }
          },
          {
            icon: () =>
              h(NIcon, null, { default: () => (row.isActive ? h(LockOff) : h(LockAccess)) })
          }
        ),
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
              deleteTeacher(row.id)
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
