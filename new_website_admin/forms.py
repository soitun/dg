from django.forms import ModelForm
from django import forms
from new_website_admin.models import Member
from django.core.files.images import get_image_dimensions


class ImageAdminForm(forms.ModelForm):
    class Meta:
        model = Member

    def clean_image(self):
        image_upload = self.cleaned_data.get("image")
        width, height = get_image_dimensions(image_upload)
        if (height > 124) and (width > 118):
            raise forms.ValidationError("""Images should be 118 pixels wide 
                                        and 124 pixels high""")
        return image_upload
