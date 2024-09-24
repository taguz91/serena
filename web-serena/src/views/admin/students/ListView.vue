<template>
  <NPageHeader title="Estudiantes" subtitle="Revisar todos los estudiantes">
    <NDataTable :columns="columns" :data="students" :loading="isLoading" />

    <div class="mt-2 flex justify-end">
      <NPagination :page-count="metaData.pages" v-model:page="currentPage" @update:page="getPage" />
    </div>
  </NPageHeader>
</template>

<script setup lang="ts">
import { useStudentsAll } from '@/composable/students/useAdminStudents'
import type { Subject } from '@/interfaces'
import { Eye } from '@vicons/tabler'
import {
  NButton,
  NDataTable,
  NIcon,
  NPageHeader,
  NPagination,
  type DataTableColumns
} from 'naive-ui'
import { h } from 'vue'

const { students, metaData, currentPage, isLoading, getPage } = useStudentsAll()

const columns: DataTableColumns<Subject> = [
  {
    title: 'Nombre',
    key: 'name'
  },
  {
    title: 'Acciones',
    key: 'actions',
    width: 100,
    className: 'flex justify-center',
    render: (row) => {
      return [
        h(
          NButton,
          {
            type: 'info',
            tertiary: true,
            onClick: () => {
              console.log('Edit', row)
            }
          },
          {
            icon: () => h(NIcon, null, { default: () => h(Eye) })
          }
        )
      ]
    }
  }
]
</script>
