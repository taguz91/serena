<template>
  <NPageHeader title="Registros" subtitle="Revisar todos los registros creados">
    <NDataTable :columns="columns" :data="registers" :loading="isLoading" />

    <div class="mt-2 flex justify-end">
      <NPagination :page-count="metaData.pages" v-model:page="currentPage" @update:page="getPage" />
    </div>
  </NPageHeader>
</template>

<script setup lang="ts">
import { useRegisterAll } from '@/composable/register/useRegisterAll'
import type { Register } from '@/interfaces'
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
import { useRouter } from 'vue-router'

const router = useRouter()

const { currentPage, isLoading, getPage, metaData, registers } = useRegisterAll()

const columns: DataTableColumns<Register> = [
  {
    title: 'Subject',
    key: 'classroom.subject.name'
  },
  {
    title: 'Teacher',
    key: 'classroom.teacher.name'
  },
  {
    title: 'Date',
    key: 'date'
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
              router.push({
                name: 'registers-report',
                params: { id: row.id }
              })
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
