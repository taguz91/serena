<template>
  <AppLayout>
    <template #side>
      <StudentList :students="students" />
    </template>

    <DetailContainer current="Historial">
      <NDataTable :columns="columns" :loading="isLoading" :data="registers" />

      <div class="mt-2 flex justify-end">
        <NPagination
          :page-count="metaData.pages"
          v-model:page="currentPage"
          @update:page="getPage"
        />
      </div>
    </DetailContainer>
  </AppLayout>
</template>

<script setup lang="ts">
import { h, toRef } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import AppLayout from '@/components/layouts/AppLayout.vue'
import DetailContainer from '@/components/containers/DetailContainer.vue'
import { useRegistersClassroom } from '@/composable/classrooms/useRegistersClassroom'
import {
  NBadge,
  NButton,
  NDataTable,
  NIcon,
  NPagination,
  useMessage,
  type DataTableColumns
} from 'naive-ui'
import type { Register } from '@/interfaces'
import { Copy } from '@vicons/tabler'
import StudentList from '@/components/shared/StudentList.vue'
import { useStudentsClassroom } from '@/composable/students/useStudentClassroom'
import { statusLabel } from '@/utils/translate'

const route = useRoute()
const router = useRouter()
const message = useMessage()

const id = toRef(route.params, 'id')

const { isLoading, metaData, currentPage, getPage, registers } = useRegistersClassroom(id)

const { students } = useStudentsClassroom(id)

const columns: DataTableColumns<Register> = [
  {
    title: 'Fecha',
    key: 'createdAt'
  },
  {
    title: 'Estado',
    key: 'status',
    render(row) {
      return h(
        NBadge,
        {
          type: row.status == 'expired' ? 'success' : 'info',
          value: statusLabel(row.status)
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
              const href = router.resolve({
                name: 'classroom-register',
                params: {
                  id: row.id
                }
              }).href

              const url = `${window.location.origin}${href}`

              const input = document.createElement('input')
              input.value = url
              document.body.appendChild(input)
              input.select()
              document.execCommand('copy')
              document.body.removeChild(input)

              message.info('Link copiado al portapapeles')
            }
          },
          {
            icon: () => h(NIcon, null, { default: () => h(Copy) })
          }
        )
      ]
    }
  }
]
</script>
