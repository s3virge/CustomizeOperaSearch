ren "C:\Program Files\Opera\52.0.2871.40\resources\default_partner_content.json" default_partner_content_OLD.json
copy "C:\Program Files\Opera\52.0.2871.40\resources\ab_tests.json" "C:\Program Files\Opera\52.0.2871.40\resources\default_partner_content.json"
del "%appdata%\Opera Software\Opera Stable\default_partner_content.json"

echo All Done. Relaunch the browser.
pause