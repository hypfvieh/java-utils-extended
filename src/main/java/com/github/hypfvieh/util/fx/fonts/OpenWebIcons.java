package com.github.hypfvieh.util.fx.fonts;

public enum OpenWebIcons implements IWebFontCode {

    APML                    (''),
    OPEN_SHARE              (''),
    OPEN_SHARE_SIMPLE       (''),
    SHARE                   (''),
    SHARE_SIMPLE            (''),
    FEED                    (''),
    FEED_SIMPLE             (''),
    OSTATUS                 (''),
    OSTATUS_SIMPLE          (''),
    OPML                    (''),
    ACTIVITY                (''),
    ACTIVITY_SIMPLE         (''),
    MICROFORMATS            (''),
    GEO                     (''),
    OPENSEARCH              (''),
    OAUTH                   (''),
    OPENID                  (''),
    SEMANTIC_WEB            (''),
    RDF                     (''),
    RDFA                    (''),
    OWL                     (''),
    DATAPORTABILITY         (''),
    FEDERATED               (''),
    WEB_INTENTS             (''),
    OPEN_WEB                (''),
    XMPP                    (''),
    HTML5                   (''),
    CSS3                    (''),
    CONNECTIVITY            (''),
    SEMANTICS               (''),
    _3DEFFECTS              (''),
    DEVICE_ACCESS           (''),
    MULTIMEDIA              (''),
    OFFLINE_STORAGE         (''),
    PERFINTEGRATION         (''),
    GIT                     (''),
    WEBHOOKS                (''),
    OSI                     (''),
    OPENSOURCE              (''),
    OPENGRAPH               (''),
    EPUB                    (''),
    QR                      (''),
    FOAF                    (''),
    INFO_CARD               (''),
    BROWSERID               (''),
    REMOTE_STORAGE          (''),
    PERSONA                 (''),
    ODATA                   (''),
    MARKDOWN                (''),
    TOSDR                   (''),
    PUB                     (''),
    SUB                     (''),
    HUBBUB                  (''),
    PUBSUBHUBBUB            (''),
    CC                      (''),
    CC_BY                   (''),
    CC_NC                   (''),
    CC_NC_EU                (''),
    CC_NC_JP                (''),
    CC_SA                   (''),
    CC_ND                   (''),
    CC_PUBLIC               (''),
    CC_ZERO                 (''),
    CC_SHARE                (''),
    CC_REMIX                (''),
    HATOM                   (''),
    HRESUME                 (''),
    HCARD_ADD               (''),
    HCARD_DOWNLOAD          (''),
    WEBFINGER               (''),
    BITCOIN                 (''),
    BITCOIN_SIMPLE          (''),
    SVG                     (''),
    JSON_LD                 (''),
    TENT                    (''),
    COPYLEFT                (''),
    GNU                     (''),
    OFL_ATTRIBUTION         (''),
    OFL_SHARE               (''),
    OFL_RENAMING            (''),
    OFL_SELLING             (''),
    OFL_EMBEDDING           (''),
    WEBMENTION              (''),
    JAVASCRIPT              (''),
    WTFPL                   (''),
    OFL                     (''),
    WORDPRESS               (''),
    OWNCLOUD                (''),
    CCCS                    (''),
    BARCAMP                 (''),
    INDIEHOSTERS            (''),
    KNOWN                   (''),
    MOZILLA                 (''),
    UNHOSTED                (''),
    DIASPORA                (''),
    INDIE                   (''),
    GHOST                   (''),
    WEBPLATTFORM            (''),
    OPENSOURCEDESIGN        (''),
    OPENDESIGN              (''),
    GNUSOCIAL               (''),
    GNUSOCIAL_SIMPLE        (''),
    MASTODON                (''),
    MASTODON_SIMPLE         (''),
    APML_COLORED            (''),
    OPEN_SHARE_COLORED      (''),
    SHARE_COLORED           (''),
    FEED_COLORED            (''),
    OSTATUS_COLORED         (''),
    OPML_COLORED            (''),
    ACTIVITY_COLORED        (''),
    MICROFORMATS_COLORED    (''),
    GEO_COLORED             (''),
    OPENSEARCH_COLORED      (''),
    HTML5_COLORED           (''),
    GIT_COLORED             (''),
    OSI_COLORED             (''),
    OPENSOURCE_COLORED      (''),
    EPUB_COLORED            (''),
    INFO_CARD_COLORED       (''),
    REMOTE_STORAGE_COLORED  (''),
    ODATA_COLORED           (''),
    PUB_COLORED             (''),
    SUB_COLORED             (''),
    HUBBUB_COLORED          (''),
    JAVASCRIPT_COLORED      (''),
    GNUSOCIAL_COLORED       (''),
    MASTODON_COLORED        (''),
    MASTODON_SIMPLE_COLORED ('');

    private final Character character;

    private OpenWebIcons(Character _character) {
        character = _character;
    }

    @Override
    public Character getCharacter() {
        return character;
    }

    @Override
    public String getFontFamily() {
        return "OpenWeb Icons";
    }

}

