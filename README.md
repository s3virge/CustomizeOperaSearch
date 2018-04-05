# CustomizeOperaSearch
task
    required to remove all search engines in Opera browser

    https://forums.opera.com/topic/14557/i-want-to-remove-yahoo-duckduckgo-amazon-bing-and-wikipedia/5

    @kaki87 You can remove them, by following the below steps:
    Open opera:about, copy the path to the profile (Paths > Profile), and make a note of the path to
    the installation root (Paths > Install), close the browser,
    Open your file explorer, and navigate to the installationroot\operaversion\resources subfolder
    (eg. installationroot\36.0.2130.46\resources), rename the default_partner_content.json to eg.
    default_partner-old, and overwrite the default_partner_content.json with ab_tests.json,
    Navigate to the profile folder, and delete the default_partner_content.json, relaunch the browser.
    In case, that the above desn't work with the ab_tests.json from your installation, you can use this one, enjoy!
