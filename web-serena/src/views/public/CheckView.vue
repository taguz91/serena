<template>
  <PublicLayout>
    <SmallSpinner v-if="isLoading" />

    <div v-else class="my-10 flex justify-center items-center flex-col">
      <p class="font-bold text-lg">Vamos a revisar si tienes una inscripción vigente</p>

      <hr class="my-2" />

      <div class="w-full mt-10">
        <NForm ref="formRef" :rules="rules" :model="model">
          <NFormItem label="Identificación/Cédula">
            <NInput v-model:value="model.identification" type="text" placeholder="0100000000" />
          </NFormItem>
        </NForm>

        <div class="flex justify-end">
          <OutlineButton
            label="Buscar"
            :loading="loading || isLoadingStudent"
            @click="handleValidateButtonClick"
          />
        </div>
      </div>

      <hr class="my-2" />

      <div v-if="checked === 'error'" class="flex justify-center">
        <NForm
          ref="formStudent"
          :model="studentForm"
          class="grid grid-cols-2 gap-3 p-4 bg-slate-200 shadow-lg rounded-xl"
        >
          <NFormItem path="identification" label="Identificación">
            <NInput v-model:value="studentForm.identification" type="text" disabled />
          </NFormItem>

          <NFormItem path="gender" label="Sexo">
            <NRadio
              :checked="studentForm.gender === 'H'"
              value="H"
              name="gender"
              @change="handleChangeGender"
            >
              Hombre
            </NRadio>
            <NRadio
              :checked="studentForm.gender === 'M'"
              value="M"
              name="gender"
              @change="handleChangeGender"
            >
              Mujer
            </NRadio>
          </NFormItem>

          <NFormItem path="name" label="Nombre">
            <NInput v-model:value="studentForm.name" type="text" placeholder="John" />
          </NFormItem>

          <NFormItem path="lastname" label="Apellido">
            <NInput v-model:value="studentForm.lastname" type="text" placeholder="Doe" />
          </NFormItem>

          <div class="flex justify-end col-span-2">
            <OutlineButton
              :disabled="model.identification === ''"
              label="Inscribirse"
              :loading="isCreating"
              @click="handleCreateStudent"
            />
          </div>
        </NForm>
      </div>

      <div v-if="checked === 'success'" class="">
        <p class="font-bold text-xl">{{ student?.name }}</p>

        <p>
          <span class="font-semibold"> El registro se creo: </span>
          <span>{{ student?.createdAt }}</span>
        </p>

        <hr class="my-2" />

        <div v-if="confirmation">
          <p class="font-semibold">Gracias por confirmar tu inscripción</p>
        </div>

        <div v-else class="flex justify-center gap-2">
          <OutlineButton
            v-if="checkedExistInscription === 'error'"
            label="Confirmar inscripción"
            @click="duplicateRegister"
          />

          <p v-else>
            <span class="font-semibold"> Ya tienes una inscripción vigente </span>
            {{ registerStudent?.createdAt }}
          </p>

          <BlackButton @click="redirectToRegister" label="Actualizar fotografía" />
        </div>
      </div>
    </div>
  </PublicLayout>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'

import { useRegister } from '@/composable/register/useRegister'
import SmallSpinner from '@/components/shared/SmallSpinner.vue'
import PublicLayout from '@/components/layouts/PublicLayout.vue'
import {
  NForm,
  type FormInst,
  type FormRules,
  NFormItem,
  NInput,
  NRadio,
  useMessage
} from 'naive-ui'
import { ref, watch } from 'vue'
import {
  useExistsRegisterStudentInscription,
  useRegisterStudentInscription
} from '@/composable/register/useRegisterStudentInscription'
import { useRegisterStudentDuplicate } from '@/composable/register/useRegisterStudentDuplicate'
import { useStudentInscription } from '@/composable/students/useStudentInscription'
import { ecuadorianIdentification } from '@/utils/validators'
import OutlineButton from '@/components/basic/OutlineButton.vue'
import BlackButton from '@/components/basic/BlackButton.vue'

const route = useRoute()
const router = useRouter()
const message = useMessage()
const confirmation = ref(false)
const { duplicate } = useRegisterStudentDuplicate(route.params.id.toString(), () => {
  confirmation.value = true
})

const { isLoading } = useRegister(route.params.id.toString())
const {
  isCreating,
  save: saveStudent,
  studentForm
} = useStudentInscription(route.params.id.toString())

const rules: FormRules = {
  identification: {
    validator: (rule, value) => {
      if (!value) {
        return new Error('La identificación es requerida')
      }

      if (value.length < 10) {
        return new Error('La identificación debe tener al menos 10 caracteres')
      }

      if (!ecuadorianIdentification(value)) {
        return new Error('La identificación no es válida')
      }

      return undefined
    }
  }
}

const formRef = ref<FormInst | null>()
const formStudent = ref<FormInst | null>()
const loading = ref(false)
const checked = ref<'error' | 'success' | 'pending' | ''>('')
const checkedExistInscription = ref<'error' | 'success' | 'pending' | ''>('')
const model = ref({
  identification: ''
})

const handleValidateButtonClick = async (e: MouseEvent) => {
  e.preventDefault()

  if (!ecuadorianIdentification(model.value.identification)) {
    message.error('La identificación no es válida')

    checked.value = ''
    confirmation.value = false
    return
  }

  formRef.value?.validate(async (errors) => {
    loading.value = true
    checked.value = ''
    confirmation.value = false
    if (!errors) {
      const status = await search()

      checked.value = status

      if (status == 'error') {
        studentForm.value.identification = model.value.identification
      } else if (student.value) {
        checkedExistInscription.value = await searchRegisterStudent()
      } else {
        studentForm.value.identification = model.value.identification
        checkedExistInscription.value = 'error'
        checked.value = 'error'
      }

      loading.value = false
    }
  })
}

const handleCreateStudent = async (e: MouseEvent) => {
  e.preventDefault()

  formStudent.value?.validate(async (errors) => {
    if (!errors) {
      saveStudent()
    }
  })
}

const handleChangeGender = (value: Event) => {
  studentForm.value.gender = (value.target as HTMLInputElement).value
}

const { isLoading: isLoadingStudent, student, search } = useRegisterStudentInscription(model)

watch(student, () => {
  if (student) {
    console.log(student.value)
  } else {
    console.log('No student')
  }
})

const { registerStudent, search: searchRegisterStudent } = useExistsRegisterStudentInscription(
  route.params.id.toString(),
  student
)

const redirectToRegister = () => {
  router.push({
    name: 'public-register-inscription-photo',
    params: {
      id: route.params.id.toString(),
      idStudent: student.value?.id
    }
  })
}

const duplicateRegister = () => {
  duplicate(student.value?.id!)
}
</script>
