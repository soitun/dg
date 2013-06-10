from django.forms import ModelForm
from django import forms
from new_website_admin.models import Member
from django.core.files.images import get_image_dimensions


class ImageAdminValidation(forms.ModelForm):
    class Meta:
        model = Member

    def clean_image(self):
        image_upload = self.cleaned_data.get("image")
        width, height = get_image_dimensions(image_upload)
        if (height > 124) and (width > 118):
            raise forms.ValidationError("""Image Should be 118 X 124
                                        in Dimension or less""")
        return image_upload
